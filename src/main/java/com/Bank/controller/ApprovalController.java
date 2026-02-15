package com.Bank.controller;

import com.Bank.dto.PendingApprovalDto;
import com.Bank.entity.Transaction;
import com.Bank.service.ApprovalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/approvals")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/pending")
    public List<PendingApprovalDto> getPendingApprovals() {
        return approvalService.getPendingWithdrawals();
    }
}
