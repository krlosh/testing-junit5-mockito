package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock(lenient = true)
    private OwnerService service;

    @InjectMocks
    private OwnerController controller;

    @Mock BindingResult result;

    //Instead of annotation you could
    // use ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(this.service.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {
                    String name = invocation.getArgument(0);
                    if(name.equals("%Buck%")) {
                        return List.of(new Owner(1L,"Joe","Buck"));
                    }
                    else if(name.equals("%DontFindMe%")) {
                        return List.of( );
                    }
                    else if(name.equals("%FindMe%")) {
                        return List.of(
                                new Owner(1L,"Joe","Buck1"),
                                new Owner(1L,"Joe","Buck1"));
                    }
                    throw new RuntimeException("Invalid arg");
                });
    }

    @Test
    void testProcessFindFormNotFound(){
        //given
        final long ID = 5L;
        Owner owner = new Owner(ID,"Joe","DontFindMe");

        //when
        String viewName = this.controller.processFindForm(owner, result,Mockito.mock(Model.class));

        //then
        assertThat("%DontFindMe%").isEqualTo(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualTo(viewName);
    }

    @Test
    void testProcessFindFormOneResultFound() {
        //given
        final long ID = 5L;
        Owner owner = new Owner(ID,"Joe","Buck");

        //when
        String viewName = this.controller.processFindForm(owner, result,Mockito.mock(Model.class));

        //then
        assertThat("%Buck%").isEqualTo(stringArgumentCaptor.getValue());
        assertThat("redirect:/owners/1").isEqualTo(viewName);
    }

    @Test
    void testProcessFindFormManyResults() {
        //given
        final long ID = 5L;
        Owner owner = new Owner(ID,"Joe","FindMe");

        //when
        String viewName = this.controller.processFindForm(owner, result,Mockito.mock(Model.class));

        //then
        assertThat("%FindMe%").isEqualTo(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualTo(viewName);
    }

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