package co.edu.uniquindio.apis.services.example;

import co.edu.uniquindio.apis.dtos.ExampleCreateDTO;
import co.edu.uniquindio.apis.dtos.ExampleResponseDTO;

import java.util.List;

public interface ExampleService {
    ExampleResponseDTO createExample(ExampleCreateDTO exampleCreateDTO);

    List<ExampleResponseDTO> listExamples();

    ExampleResponseDTO getExampleById(Long id);

    ExampleResponseDTO updateExample(Long id, ExampleCreateDTO exampleCreateDTO);

    boolean deleteExample(Long id);
}