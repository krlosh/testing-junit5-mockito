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
        //given
        given(this.repository.findAll()).willReturn(new ArrayList<>());

        //when
        Set<Visit> emptyVisits = this.service.findAll();

        //then
        assertThat(emptyVisits).isEmpty();
        then(this.repository).should().findAll();
    }

    @Test
    void findAllManyReturns() {
        //given
        given(this.repository.findAll()).willReturn(List.of(new Visit(), new Visit()));

        //when
        Set<Visit> allVisits = this.service.findAll();

        //then
        assertThat(allVisits).hasSize(2);
        then(this.repository).should().findAll();
    }

    @Test
    void findByIdNotFound() {
        //when
        Visit visit = this.service.findById(1L);

        //then
        assertThat(visit).isNull();
        then(this.repository).should().findById(1L);
    }

    @Test
    void findByIdBDD(){
        //given
        given(this.repository.findById(1L)).willReturn(Optional.of(new Visit()));

        //when
        Visit foundVisit = this.service.findById(1L);

        //then
        assertThat(foundVisit).isNotNull();
        then(this.repository).should().findById(anyLong());
        then(this.repository).shouldHaveNoMoreInteractions();

    }

    @Test
    void findByIdFound() {
        //given
        given(this.repository.findById(1L)).willReturn(Optional.of(new Visit()));

        //when
        Visit visit = this.service.findById(1L);

        //then
        assertThat(visit).isNotNull();
        then(this.repository).should().findById(1L);
    }

    @Test
    void save() {
        //given
        Visit visitToSave = new Visit();
        given(this.repository.save(any(Visit.class))).willReturn(visitToSave);

        //when
        Visit visit = this.service.save(visitToSave);

        //then
        assertThat(visit).isNotNull();
        then(this.repository).should().save(any(Visit.class));
    }

    @Test
    void delete() {
        //when
        this.service.delete(new Visit());

        //then
        then(this.repository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        //when
        this.service.deleteById(1L);

        //then
        then(this.repository).should().deleteById(anyLong());
    }
}