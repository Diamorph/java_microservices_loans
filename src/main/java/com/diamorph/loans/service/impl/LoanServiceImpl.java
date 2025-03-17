package com.diamorph.loans.service.impl;

import com.diamorph.loans.constants.LoansConstants;
import com.diamorph.loans.dto.LoansDto;
import com.diamorph.loans.enitity.Loans;
import com.diamorph.loans.exception.LoanAlreadyExistsException;
import com.diamorph.loans.exception.ResourceNotFoundException;
import com.diamorph.loans.mapper.LoansMapper;
import com.diamorph.loans.repository.LoansRepository;
import com.diamorph.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Service
public class LoanServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loansRepository.save(this.createNewLoan(mobileNumber));
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = this.findLoansByMobileNumberOrThrowResourceNotFoundException(mobileNumber);
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = this.findLoansByLoanNumberOrThrowResourceNotFoundException(loansDto.getLoanNumber());
        Loans updatedLoans = LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(updatedLoans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = this.findLoansByMobileNumberOrThrowResourceNotFoundException(mobileNumber);
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

    private Loans findLoansByMobileNumberOrThrowResourceNotFoundException(String mobileNumber) {
        return loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    }

    private Loans findLoansByLoanNumberOrThrowResourceNotFoundException(String loanNumber) {
        return loansRepository.findByLoanNumber(loanNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }


}
