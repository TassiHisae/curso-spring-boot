package com.springboot.curso.Spring.Boot.Curso.controllers;

import com.springboot.curso.Spring.Boot.Curso.models.Convidado;
import com.springboot.curso.Spring.Boot.Curso.models.Evento;
import com.springboot.curso.Spring.Boot.Curso.repository.ConvidadoRepository;
import com.springboot.curso.Spring.Boot.Curso.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @GetMapping("/cadastrarEvento")
    public String form(){
        return "evento/formEvento";
    }

    @PostMapping("/cadastrarEvento")
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/cadastrarEvento";
        }

        eventoRepository.save(evento);
        attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
        return "redirect:/cadastrarEvento";
    }

    @GetMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = eventoRepository.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @GetMapping("/{codigo}")
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        mv.addObject("convidados", convidados);

        return mv;
    }

    @PostMapping("/{codigo}")
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/{codigo}";
        }
        Evento evento = eventoRepository.findByCodigo(codigo);
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
        return "redirect:/{codigo}";
    }
}
