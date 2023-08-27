package org.chun.classify.utils;

import lombok.SneakyThrows;
import org.chun.classify.exception.DocumentReadFailureException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentUtil {

	@SneakyThrows

	public static void write(String path, String content){
		if(isExists(path)){
			try (FileOutputStream fos = new FileOutputStream(path);
			     BufferedOutputStream bos = new BufferedOutputStream(fos)) {
				byte[] data = content.getBytes();
				bos.write(data);
			} catch (Exception e) {
				throw new DocumentReadFailureException(e, path);
			}
		}
	}

	@SneakyThrows
	public static List<String[]> contents(String path) {
		if (isExists(path)) {
			List<String[]> contents = new ArrayList<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(file(path)))) {
				String content;
				while ((content = reader.readLine()) != null) {
					contents.add(content.split(","));
				}
			} catch (Exception e) {
				throw new DocumentReadFailureException(e, path);
			}
			return contents;
		}
		return Collections.emptyList();
	}

	public static boolean isExists(String path) {
		return file(path).exists();
	}

	private static File file(String path) {
		return new File(path);
	}
}
