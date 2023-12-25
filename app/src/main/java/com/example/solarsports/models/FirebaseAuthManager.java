package com.example.solarsports.models;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthManager {

    private FirebaseAuth mAuth;
    private static FirebaseAuthManager instance;

    private FirebaseAuthManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseAuthManager getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthManager();
        }
        return instance;
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

    public void signOut() {
        mAuth.signOut();
    }

    // Otros métodos útiles, como signIn, signOut, getCurrentUser, etc.
}
