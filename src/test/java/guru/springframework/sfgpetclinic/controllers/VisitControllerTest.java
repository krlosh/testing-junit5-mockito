package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    private VisitService visitService;

    @Spy
    private PetMapService petService;

    @InjectMocks
    private VisitController controller;

    @Test
    void loadPetWithVisit() {
        //given
        Pet pet = new Pet();
        petService.save(pet);
        given(this.petService.findById(anyLong())).willCallRealMethod();

        //when
        Visit loadedVisit = this.controller.loadPetWithVisit(1L,new HashMap<>());

        //then
        assertThat(loadedVisit).isNotNull();
        assertThat(loadedVisit.getPet().getId()).isEqualTo(1L);
        then(this.petService).should().findById(anyLong());
    }
}