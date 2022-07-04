package com.example.vaccination.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.entities.PtEntities;

@Mapper
public interface PtMapper 
{
	PtMapper INSTANCE = Mappers.getMapper(PtMapper.class);
	
	PtDto ptEntityToptDto(PtEntities ptEntity);
	PtEntities ptDtoToptEntity(PtDto ptDto);
	List<PtDto> toPtDto(List<PtEntities> ptEntities);
}
