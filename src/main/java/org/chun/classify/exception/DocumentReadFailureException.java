package org.chun.classify.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@Getter
@AllArgsConstructor
public class DocumentReadFailureException extends IOException {
	private Throwable e;
	private String filePath;
}
