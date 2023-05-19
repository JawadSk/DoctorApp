package com.geekster.doctorApp.service;


import com.geekster.doctorApp.dto.SignInInput;
import com.geekster.doctorApp.dto.SignInOutput;
import com.geekster.doctorApp.dto.SignUpInput;
import com.geekster.doctorApp.dto.SignUpOutput;
import com.geekster.doctorApp.model.AppointmentKey;
import com.geekster.doctorApp.model.AuthenticationToken;
import com.geekster.doctorApp.model.Doctor;
import com.geekster.doctorApp.model.Patient;
import com.geekster.doctorApp.repository.IPatientRepo;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepo iPatientRepo;


    @Autowired
    AuthenticationService tokenService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    public SignUpOutput signUp(SignUpInput signUpDto) {

        //check if user exists or not based on email
        Patient patient = iPatientRepo.findFirstByPatientEmail(signUpDto.getUserEmail());

        if(patient != null)
        {
            throw new IllegalStateException("Patient already exists!!!!...sign in instead");
        }


        //encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }



        patient = new Patient(signUpDto.getUserFirstName(),
                signUpDto.getUserLastName(),signUpDto.getUserEmail(),
                encryptedPassword, signUpDto.getUserContact());

        iPatientRepo.save(patient);



        AuthenticationToken token = new AuthenticationToken(patient);

        tokenService.saveToken(token);

        return new SignUpOutput("Patient registered","Patient created successfully");


    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userPassword.getBytes());
        byte[] digested =  md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        return hash;
    }


    public SignInOutput signIn(SignInInput signInDto) {



        Patient patient = iPatientRepo.findFirstByPatientEmail(signInDto.getPatientEmail());

        if(patient == null)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }



        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signInDto.getPatientPassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }





        boolean isPasswordValid = encryptedPassword.equals(patient.getPatientPassword());

        if(!isPasswordValid)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }



        AuthenticationToken authToken = tokenService.getToken(patient);

   

        return new SignInOutput("Authentication Successfull !!!",authToken.getToken());


    }

    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    public void cancelAppointment(AppointmentKey key) {

        appointmentService.cancelAppointment(key);

    }
}
