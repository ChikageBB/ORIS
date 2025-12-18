package ru.itis.dis403.lab05.controller;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(urlPatterns = {
        "/index",
        "/login",
        "/logout"
})
public class AuthController extends HttpServlet {

}
