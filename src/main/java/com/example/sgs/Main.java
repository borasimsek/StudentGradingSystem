package com.example.sgs;

import com.example.sgs.Entities.User;
import com.example.sgs.Repository.UserRepository;
import com.example.sgs.View.StudentDashboard;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        new StudentDashboard().setVisible(true);
    }
}
