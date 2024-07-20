
import static org.assertj.core.api.Assertions.assertThat;

import com.motta.insurance_scheme_service.entity.Scheme;
import com.motta.insurance_scheme_service.mapper.SchemeMapper;
import com.motta.insurance_scheme_service.model.SchemeDTO;
import com.motta.insurance_scheme_service.repository.SchemeRepository;
import com.motta.insurance_scheme_service.service.SchemeService;
import com.motta.insurance_scheme_service.service.SchemeServiceImplementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Date;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class InsuranceSchemeTest {

    @Mock
    private SchemeRepository schemeRepository;

    @InjectMocks
    private SchemeServiceImplementation schemeService;


    @DisplayName("JUnit test for getScheme method")
    @Test
    public void getSchemeByIdTest(){

        Scheme scheme = new Scheme(1,"Full Family Coverage", new Date(), new Date(), 50000.0, "ANNUAL");
        // given
        when(schemeRepository.findById(scheme.getId())).thenReturn(Optional.of(scheme));

        // when
        SchemeDTO savedSchemeDTO = schemeService.retrieveSchemeById( (scheme.getId()) );

        // then
        assertThat(savedSchemeDTO).isNotNull();
    }

    @DisplayName("JUnit test for getAllSchemes method")
    @Test
    public void getAllSchemes(){
        // given - precondition or setup

        Scheme s1 = new Scheme(1,"Full Family Coverage", new Date(), new Date(), 50000.0, "ANNUAL");
        Scheme s2 = new Scheme(2,"Happiness Unlimited", new Date(), new Date(), 60000.0, "MONTHLY");
        Scheme s3 = new Scheme(3,"Freedom Package", new Date(), new Date(), 70000.0, "QUARTERLY");

        when(schemeRepository.findAll()).thenReturn(List.of(s1,s2,s3));

        // when -  action or the behaviour that we are going test
        List<SchemeDTO> schemeList = schemeService.retrieveAllSchemes();

        // then - verify the output
        assertThat(schemeList).isNotNull();
        assertThat(schemeList.size()).isEqualTo(3);
    }



    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for createScheme method")
    @Test
    public void createScheme(){


        // Initial Set up
        SchemeDTO schemeDTO = new SchemeDTO(1007,"Full Family Coverage", new Date(), new Date(), 50000.0, "ANNUAL");
        Scheme newScheme = SchemeMapper.mapToScheme(schemeDTO);

        when(schemeRepository.save(any())).thenReturn(newScheme);
        Optional<SchemeDTO> savedScheme = Optional.ofNullable(schemeService.createScheme(schemeDTO));
        assertTrue(savedScheme.isPresent());

    }
    @DisplayName("JUnit test for updateScheme method")
    @Test
    public void updateScheme(){

        // Initial Set up
        SchemeDTO schemeDTO = new SchemeDTO(1007,"Full Family Coverage", new Date(), new Date(), 50000.0, "ANNUAL");
        Scheme newScheme = SchemeMapper.mapToScheme(schemeDTO);

        when(schemeRepository.save(any())).thenReturn(newScheme);
        schemeDTO.setName("New");
        Optional<SchemeDTO> updatedScheme = Optional.ofNullable(schemeService.updateScheme(schemeDTO));
        assertTrue(updatedScheme.isPresent());
        assertThat(updatedScheme.get().getName()).isEqualTo("New");

    }

    @DisplayName("JUnit test for deleteScheme method")
    @Test
    public void deleteScheme(){
        // given - precondition or setup
        int schemeId = 1;

        doNothing().when(schemeRepository).deleteById(schemeId);

        // when -  action or the behaviour that we are going test
        schemeService.deleteScheme(schemeId);

        // then - verify the output
        verify(schemeRepository, times(1)).deleteById(schemeId);
    }

}