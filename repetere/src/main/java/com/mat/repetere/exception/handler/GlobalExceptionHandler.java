package com.mat.repetere.exception.handler;

import com.mat.repetere.exception.CardNotFoundException;
import com.mat.repetere.exception.DeckNotFoundException;
import com.mat.repetere.exception.UnauthorizedRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            DeckNotFoundException.class,
            CardNotFoundException.class,
            NoResourceFoundException.class
    })
    public String handleNotFound(HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("errorTitle", "No encontrado");
        model.addAttribute("errorMessage", "No encontramos lo que estabas buscando.");
        return resolveView(request);
    }

    @ExceptionHandler({AccessDeniedException.class, UnauthorizedRequest.class})
    public String handleAccessDenied(HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        model.addAttribute("errorTitle", "Sin permiso");
        model.addAttribute("errorMessage", "No tenés permiso para acceder a esta página.");
        return resolveView(request);
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception e, HttpServletRequest request, HttpServletResponse response, Model model) {
        log.error("Error inesperado", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("errorTitle", "Algo salió mal");
        model.addAttribute("errorMessage", "Ocurrió un error inesperado. Probá de nuevo en unos minutos.");
        return resolveView(request);
    }

    private String resolveView(HttpServletRequest request) {
        if (request.getHeader("HX-Request") != null) {
            return "error/error :: content";
        }
        return "error/error :: page";
    }
}