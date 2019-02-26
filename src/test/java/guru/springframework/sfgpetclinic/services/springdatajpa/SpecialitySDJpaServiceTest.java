package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    private SpecialtyRepository repository;

    @InjectMocks
    private SpecialitySDJpaService service;

    @Test
    void findByIdTest() {
        //given
        Speciality speciality = new Speciality();
        given(this.repository.findById(1L)).willReturn(Optional.of(speciality));

        //when
        Speciality found = this.service.findById(1L);

        //then
        assertNotNull(found);
        then(this.repository).should().findById(1L);
    }

    @Test
    void deleteById() {
        //when
        this.service.deleteById(1l);

        //then
        then(this.repository).should().deleteById(1l);
        then(this.repository).should(never()).delete(any());
    }

    @Test
    void delete() {
        //given
        Speciality objectToDelete = new Speciality();

        //when
        this.service.delete(objectToDelete);

        //then
        then(this.repository).should().delete(objectToDelete);
        then(this.repository).should(never()).deleteById(anyLong());
    }
}