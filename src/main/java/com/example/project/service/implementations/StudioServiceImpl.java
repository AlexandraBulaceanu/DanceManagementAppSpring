package com.example.project.service.implementations;

import com.example.project.constants.Constants;
import com.example.project.dto.StudioDetailsDto;
import com.example.project.dto.StudioDto;
import com.example.project.entity.Studio;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.mapper.StudioMapper;
import com.example.project.repository.StudioRepository;
import com.example.project.service.CommonService;
import com.example.project.service.StudioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudioServiceImpl implements StudioService, CommonService<StudioDto, StudioDto> {
    private final StudioRepository studioRepository;

    private final StudioMapper studioMapper;

    @Autowired
    public StudioServiceImpl(StudioRepository studioRepository, StudioMapper studioMapper) {
        this.studioRepository = studioRepository;
        this.studioMapper = studioMapper;
    }

    @Override
    public List<StudioDetailsDto> getStudios() {
        return studioRepository.findAll().stream().map(studioMapper::mapToStudioDetailsDto).collect(Collectors.toList());
    }

    @Override
    public StudioDto add(StudioDto studioDto) {
        Optional<Studio> optionalStudio = studioRepository.findByName(studioDto.getName());
        if(optionalStudio.isPresent()) {
            throw new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name"));
        }
        return studioMapper.mapToStudioDto(studioRepository.save(studioMapper.mapToStudio(studioDto)));
    }

    @Override
    public StudioDto editStudio(Long id, StudioDto studioDto) {
        Optional<Studio> optionalStudio = studioRepository.findById(id);
        if(!optionalStudio.isPresent()) {
            throw new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "studio", "id", id));
        }

        Optional<Studio> optionalStudioByName = studioRepository.findByName(studioDto.getName());
        if(optionalStudioByName.isPresent() && !Objects.equals(optionalStudioByName.get().getStudio_id(), id)) {
            throw new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name"));
        }

        Studio studio = optionalStudio.get();
        studio.setName(studioDto.getName());

        return studioMapper.mapToStudioDto(studioRepository.save(studio));
    }
}