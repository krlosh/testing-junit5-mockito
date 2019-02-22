package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    @Mock
    private VetRepository repository;

    @InjectMocks
    private VetSDJpaService vetService;

    @Test
    void delete() {
        Vet vet = new Vet(1L,"","",null);
        this.vetService.delete(vet);
        verify(this.repository).delete(vet);
        verify(this.repository, never()).deleteById(anyLong());
    }

    @Test
    void deleteById() {
        this.vetService.deleteById(1L);
        verify(this.repository).deleteById(1L);
        verify(this.repository, never()).delete(any());
    }
}