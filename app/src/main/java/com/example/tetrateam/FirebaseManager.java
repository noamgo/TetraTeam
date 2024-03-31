package com.example.tetrateam;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


/**
 * FirebaseManager
 * This class is used to directly interact with Firebase.
 *
 * @version 1.0
 */
public class FirebaseManager {
public static void signOut( ) {
	FirebaseAuth firebaseAuth = FirebaseAuth.getInstance( );
	firebaseAuth.signOut( );
}

public static boolean isSignedIn( ) {
	return FirebaseAuth.getInstance( ).getCurrentUser( ) != null;
}

public static String getUserUsername( ) {
	return FirebaseAuth.getInstance( ).getCurrentUser( ).getDisplayName( );
}

// returns high score from database
//public int getUserHighScore( ) {
	//return FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("highScore").getValue(Integer.class);
//}

public static String getUserEmail( ) {
	return FirebaseAuth.getInstance( ).getCurrentUser( ).getEmail( );
}


public static String getCurrentUserUid( ) {
	return FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( );
}

public static String getCurrentUserDisplayName( ) {
	return FirebaseAuth.getInstance( ).getCurrentUser( ).getDisplayName( );
}

public static Task<AuthResult> signIn( String email, String password ) {
	return FirebaseAuth.getInstance( ).signInWithEmailAndPassword(email, password);
}

public static Task<Void> sendPasswordReset( String email ) {
	return FirebaseAuth.getInstance( ).sendPasswordResetEmail(email);
}
}

