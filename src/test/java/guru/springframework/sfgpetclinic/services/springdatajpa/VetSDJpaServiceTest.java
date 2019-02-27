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
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    @Mock
    private VetRepository repository;

    @InjectMocks
    private VetSDJpaService vetService;

    @Test
    void delete() {
        //given
        Vet vet = new Vet(1L,"","",null);

        //when
        this.vetService.delete(vet);

        //then
        then(this.repository).should().delete(vet);
        then(this.repository).should(never()).deleteById(anyLong());
    }

    @Test
    void deleteById() {
        //when
        this.vetService.deleteById(1L);

        //then
        then(this.repository).should().deleteById(1L);
        then(this.repository).should(never()).delete(any());
    }
}