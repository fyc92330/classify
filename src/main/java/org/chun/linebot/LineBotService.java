package org.chun.linebot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.error.ErrorResponse;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.profile.UserProfileResponse;
import io.micrometer.common.util.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.util.Strings;
import org.chun.classify.exception.LineReplyMessageExpiredException;
import org.chun.classify.exception.LineServerException;
import org.chun.classify.exception.ServerUnKnownException;
import org.springframework.http.MediaType;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;


@Slf4j
public class LineBotService implements ILineBotService {
	private static final ObjectMapper MAPPER = new ObjectMapper()
			.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
			.enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME)
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String INVALID_REPLY_TOKEN = "Invalid reply token";
	private static final String FAILED_TO_SEND_MESSAGES = "Failed to send messages";
	private static final String SUCCESS_RESPONSE_BODY = "{}";
	private final LineBotApi lineBotApi;
	private final String channelAccessToken;

	public LineBotService(String baseUrl, String channelAccessToken) {
		this.channelAccessToken = TOKEN_PREFIX.concat(channelAccessToken);
		this.lineBotApi = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(JacksonConverterFactory.create(MAPPER))
				.build()
				.create(LineBotApi.class);
	}

	@Override
	public UserProfileResponse profile(String userId) {
		Call<ResponseBody> call = lineBotApi.profile(channelAccessToken, userId);
		return convertResult(call, UserProfileResponse.class);
	}

	@Override
	public void reply(ReplyMessage replyMessage, String userId) {
		Call<ResponseBody> call = lineBotApi.reply(replyMessage, CONTENT_TYPE, channelAccessToken);
		sendMessage(call, replyMessage.getMessages(), userId);
	}

	@Override
	public void push(PushMessage pushMessage) {
		Call<ResponseBody> call = lineBotApi.push(pushMessage, CONTENT_TYPE, channelAccessToken);
		sendMessage(call);
	}

	@Override
	public void menuChange(String userId, String menuId) {
		Call<ResponseBody> call = lineBotApi.menuChange(channelAccessToken, userId, menuId);
		execute(call);
	}

	/**
	 * =================================================== private ==================================================
	 */

	private <T> T convertResult(Call<ResponseBody> call, Class<T> resultType) {
		return this.convertResult(this.execute(call), resultType);
	}

	private void sendMessage(Call<ResponseBody> call) {
		this.sendMessage(call, null, null);
	}

	@SneakyThrows
	private void sendMessage(Call<ResponseBody> call, List<Message> messages, String userId) {
		try {
			this.execute(call);
		} catch (Exception e) {
			if (e instanceof LineServerException) {
				ErrorResponse errorResponse = ((LineServerException) e).getErrorResponse();
				String errorMessage = errorResponse.getMessage();
				if (INVALID_REPLY_TOKEN.equals(errorMessage) || FAILED_TO_SEND_MESSAGES.equals(errorMessage)) {
					throw new LineReplyMessageExpiredException(messages, userId);
				}
			}
			throw e;
		}
	}

	@SneakyThrows
	private String execute(Call<ResponseBody> call) {
		String responseContent = Strings.EMPTY;
		try {
			Response<ResponseBody> response = call.execute();
			ResponseBody responseBody = response.body();
			responseContent = responseBody.string();
			if(StringUtils.isBlank(responseContent)) throw new ServerUnKnownException("Response is Null. ");
			return responseContent;
		} catch (LineServerException e) {
			e.setRequest(call.request());
			e.setErrorResponse(this.convertResult(responseContent, ErrorResponse.class));
			throw e;
		} catch (Exception e) {
			log.error("", e);
			throw new ServerUnKnownException(e.getMessage());
		}
	}

	private <T> T convertResult(String json, Class<T> resultType) {
		try {
			return MAPPER.readValue(json, resultType);
		} catch (Exception e) {
			throw new LineServerException(String.format("convertResult error. json: %s, resultType: %s", json, resultType));
		}
	}

}
