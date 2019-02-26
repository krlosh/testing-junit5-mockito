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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    private SpecialtyRepository repository;

    @InjectMocks
    private SpecialitySDJpaService service;

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();
        when(this.repository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality found = this.service.findById(1L);
        assertNotNull(found);
        verify(this.repository).findById(1L);
    }

    @Test
    void deleteById() {
        this.service.deleteById(1l);
        verify(this.repository).deleteById(1l);
        verify(this.repository, never()).delete(any());
    }

    @Test
    void delete() {

        Speciality objectToDelete = new Speciality();
        this.service.delete(objectToDelete);
        verify(this.repository).delete(objectToDelete);
        verify(this.repository, never()).deleteById(anyLong());
    }
}