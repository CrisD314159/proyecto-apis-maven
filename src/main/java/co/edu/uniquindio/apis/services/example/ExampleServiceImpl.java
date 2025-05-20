package co.edu.uniquindio.apis.services.example;

import co.edu.uniquindio.apis.dtos.ExampleCreateDTO;
import co.edu.uniquindio.apis.dtos.ExampleResponseDTO;
import co.edu.uniquindio.apis.exceptions.EntityNotFoundException;
import co.edu.uniquindio.apis.mappers.domainMappers.ExampleMapper;
import co.edu.uniquindio.apis.model.Example;
import co.edu.uniquindio.apis.repositories.example.ExampleRepository;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExampleServiceImpl implements ExampleService {

    @Inject
    ExampleRepository exampleRepository;

    @Inject
    ExampleMapper exampleMapper;

    @Inject
    MeterRegistry meterRegistry;

    @Override
    @Transactional
    public ExampleResponseDTO createExample(ExampleCreateDTO exampleCreateDTO) {
        Example example = exampleMapper.toEntity(exampleCreateDTO);
        example.setCreationDate(LocalDateTime.now());

        exampleRepository.persist(example);

        meterRegistry.counter("apis.example.created").increment();
        return exampleMapper.toResponseDTO(example);
    }

    @Override
    public List<ExampleResponseDTO> listExamples() {
        return exampleRepository.listAll().stream()
                .map(exampleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExampleResponseDTO getExampleById(Long id) {
        return exampleMapper.toResponseDTO(findExampleOrThrow(id));
    }

    @Override
    @Transactional
    public ExampleResponseDTO updateExample(Long id, ExampleCreateDTO exampleCreateDTO) {
        Example example = findExampleOrThrow(id);

        example.setTitle(exampleCreateDTO.title());
        example.setDescription(exampleCreateDTO.description());
        example.setContent(exampleCreateDTO.content());
        example.setTags(exampleCreateDTO.tags());
        example.setDifficulty(exampleCreateDTO.difficulty());

        exampleRepository.persist(example);
        return exampleMapper.toResponseDTO(example);
    }

    @Override
    @Transactional
    public boolean deleteExample(Long id) {
        boolean deleted = exampleRepository.deleteById(id);
        if (!deleted) {
            throw new EntityNotFoundException("Example with id " + id + " not found");
        }
        meterRegistry.counter("apis.example.deleted").increment();
        return true;
    }

    // Método privado para evitar repetición de lógica
    private Example findExampleOrThrow(Long id) {
        Example example = exampleRepository.findById(id);
        if (example == null) {
            throw new EntityNotFoundException("Example with id " + id + " not found");
        }
        return example;
    }
}
