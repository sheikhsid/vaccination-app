package com.example.vaccination.service;

import com.example.vaccination.dto.*;
import java.util.*;

public interface PtService 
{
	
	List<PtDto> getAllPt();

	PtDto createNewPt(PtDto pt);

	PtDto updatePtById(long ptId, PtDto ptDto);
	
	PtDto getPtById(long ptId);

}
