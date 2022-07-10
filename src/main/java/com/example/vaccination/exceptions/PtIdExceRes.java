package com.example.vaccination.exceptions;



public class PtIdExceRes {

    private final Long ptId;

    public PtIdExceRes(Long ptId) {
        this.ptId = ptId;
    }

    public Long getId() {
    	return ptId;
		
	}
    
}
