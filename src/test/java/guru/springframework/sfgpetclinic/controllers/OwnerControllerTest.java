package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock
    private OwnerService service;

    @InjectMocks
    private OwnerController controller;

    @Mock BindingResult result;

    @Test
    void testSuccessfullyProcessCreationForm() {
        //given
        final long ID = 5L;
        Owner createdOwner = new Owner(ID,"","");
        given(service.save(any(Owner.class))).willReturn(createdOwner);
        given(result.hasErrors()).willReturn(Boolean.FALSE);

        //when
        String form = this.controller.processCreationForm(createdOwner, result);

        //then
        assertThat(form).isEqualTo(REDIRECT_OWNERS_5);
        then(service).should().save(any(Owner.class));
    }

    @Test
    void testFailedProcessCreationForm() {
        //given
        final long ID = 5L;
        Owner createdOwner = new Owner(ID,"","");
        given(result.hasErrors()).willReturn(Boolean.TRUE);

        //when
        String form = this.controller.processCreationForm(createdOwner, result);

        //then
        assertThat(form).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
        then(service).should(never()).save(any(Owner.class));
    }
}