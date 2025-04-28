package com.eouil.bank.bankapi.controllers;

import com.eouil.bank.bankapi.dtos.requests.CreateAccountRequest;
import com.eouil.bank.bankapi.dtos.responses.CreateAccountResponse;
import com.eouil.bank.bankapi.dtos.responses.GetMyAccountResponse;
import com.eouil.bank.bankapi.services.AccountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.eouil.bank.bankapi.metrics.SecurityMetrics;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private final SecurityMetrics securityMetrics;
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<CreateAccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest createRequest) {

        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[POST /accounts] 계좌 생성 요청 - 사용자 ID: {}, 요청 정보: {}", userId, createRequest);

        CreateAccountResponse response = accountService.createAccount(createRequest, userId);

        log.info("[POST /accounts] 계좌 생성 완료 - 계좌 번호: {}", response.getAccountNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts/me")
    public ResponseEntity<List<GetMyAccountResponse>> getMyAccount(
            @RequestHeader("Authorization") String token) {

        log.info("[GET /accounts/me] 내 계좌 목록 조회 요청 (토큰 일부: {}...)", token.substring(0, Math.min(10, token.length())));
        List<GetMyAccountResponse> responses = accountService.getMyaccount(token);

        // 민감 데이터 접근 메트릭 추가
        securityMetrics.incrementSensitiveDataAccess();

        log.info("[GET /accounts/me] 내 계좌 {}건 조회 완료", responses.size());

        return ResponseEntity.ok(responses);
    }