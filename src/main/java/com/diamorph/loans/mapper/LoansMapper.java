package com.diamorph.loans.mapper;

import com.diamorph.loans.dto.LoansDto;
import com.diamorph.loans.enitity.Loans;

public class LoansMapper {

    public static LoansDto mapToLoansDto(Loans loans, LoansDto loansDto) {
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDto;
    }

    public static Loans mapToLoans(LoansDto loansDto, Loans loans) {
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loans;
    }
}
