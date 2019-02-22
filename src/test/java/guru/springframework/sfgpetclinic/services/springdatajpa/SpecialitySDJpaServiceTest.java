package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    private SpecialtyRepository repository;

    @InjectMocks
    private SpecialitySDJpaService service;

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