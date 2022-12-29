package com.example.application.views.list;

import com.example.application.data.entity.Doctor;
import com.example.application.data.entity.Hospital;

import com.example.application.data.entity.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DoctorFormTest {
    private List<Hospital> hospitals;
    private List<Type> types;
    private Doctor marcUsher;
    private Hospital hospital1;
    private Hospital hospital2;
    private Type type1;
    private Type type2;

    @Before
    public void setupData() {
        hospitals = new ArrayList<>();
        hospital1 = new Hospital();
        hospital1.setName("Vaadin Ltd");
        hospital2 = new Hospital();
        hospital2.setName("IT Mill");
        hospitals.add(hospital1);
        hospitals.add(hospital2);

        types = new ArrayList<>();
        type1 = new Type();
        type1.setName("Type 1");
        type2 = new Type();
        type2.setName("Type 2");
        types.add(type1);
        types.add(type2);

        marcUsher = new Doctor();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setType(type1);
        marcUsher.setHospital(hospital2);
    }

    @Test
    public void formFieldsPopulated() {
        DoctorForm form = new DoctorForm(hospitals, types);
        form.setDoctor(marcUsher);
        Assert.assertEquals("Marc", form.firstName.getValue());
        Assert.assertEquals("Usher", form.lastName.getValue());
        Assert.assertEquals("marc@usher.com", form.email.getValue());
        Assert.assertEquals(hospital2, form.hospital.getValue());
        Assert.assertEquals(type1, form.type.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        DoctorForm form = new DoctorForm(hospitals, types);
        Doctor doctor = new Doctor();
        form.setDoctor(doctor);
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.hospital.setValue(hospital1);
        form.email.setValue("john@doe.com");
        form.type.setValue(type2);

        AtomicReference<Doctor> savedDoctorRef = new AtomicReference<>(null);
        form.addListener(DoctorForm.SaveEvent.class, e -> {
            savedDoctorRef.set(e.getDoctor());
        });
        form.save.click();
        Doctor savedDoctor = savedDoctorRef.get();

        Assert.assertEquals("John", savedDoctor.getFirstName());
        Assert.assertEquals("Doe", savedDoctor.getLastName());
        Assert.assertEquals("john@doe.com", savedDoctor.getEmail());
        Assert.assertEquals(hospital1, savedDoctor.getHospital());
        Assert.assertEquals(type2, savedDoctor.getType());
    }
}