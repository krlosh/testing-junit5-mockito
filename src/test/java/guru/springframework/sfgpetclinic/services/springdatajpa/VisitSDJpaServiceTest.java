package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    private VisitRepository repository;

    @InjectMocks
    private  VisitSDJpaService service;

    @Test
    void findAllThatNoneIsFound() {
        when(this.repository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.service.findAll().isEmpty());
        verify(this.repository).findAll();
    }

    @Test
    void findAllManyReturns() {
        when(this.repository.findAll()).thenReturn(List.of(new Visit(), new Visit()));
        Set<Visit> allVisits = this.service.findAll();
        assertFalse(allVisits.isEmpty());
        assertEquals(2, allVisits.size());
        verify(this.repository).findAll();
    }

    @Test
    void findByIdNotFound() {
        assertNull(this.service.findById(1L));
        verify(this.repository).findById(1L);
    }

    @Test
    void findByIdBDD(){
        //given
        given(this.repository.findById(1L)).willReturn(Optional.of(new Visit()));

        //when
        Visit foundVisit = this.service.findById(1L);

        //then
        assertThat(foundVisit).isNotNull():
        then(this.repository).should().findById(anyLong());
        then(this.repository).shouldHaveNoMoreInteractions();

    }

    @Test
    void findByIdFound() {
        when(this.repository.findById(1L)).thenReturn(Optional.of(new Visit()));
        assertNotNull(this.service.findById(1L));
        verify(this.repository).findById(1L);
    }

    @Test
    void save() {
        Visit visitToSave = new Visit();
        when(this.repository.save(any(Visit.class))).thenReturn(visitToSave);
        assertNotNull(this.service.save(visitToSave));
        verify(this.repository).save(any(Visit.class));
    }

    @Test
    void delete() {
        this.service.delete(new Visit());
        verify(this.repository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        this.service.deleteById(1L);
        verify(this.repository).deleteById(anyLong());
    }
}