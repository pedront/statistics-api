package com.n26.statistics.api.controller;

import static com.n26.statistics.api.controller.TransactionController.BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.n26.statistics.api.controller.request.TransactionRequest;
import com.n26.statistics.api.service.StatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Transactions controller", tags = {"Transactions"})
@RestController
@RequestMapping(value = BASE_URL, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class TransactionController {

    public static final String BASE_URL = "/transactions";

    private final StatisticsService statisticsService;

    @ApiOperation(value = "Insert new transaction", //
        notes =
            "Inserts new transaction that will be used to generate statistics.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Success"),
        @ApiResponse(code = 204, message = "Transaction older than 60 seconds")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@Valid @RequestBody TransactionRequest
        transactionRequest) {

        statisticsService.put(transactionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
