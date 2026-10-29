package com.victorvillar.baozistore.exception;

import java.time.Instant;

public record ApiError(Instant timestamp, int status, String mensagem, String caminho) { }
