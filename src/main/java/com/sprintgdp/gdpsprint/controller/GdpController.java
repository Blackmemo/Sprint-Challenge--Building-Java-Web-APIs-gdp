package com.sprintgdp.gdpsprint.controller;

import com.sprintgdp.gdpsprint.GdpList;
import com.sprintgdp.gdpsprint.GdpsprintApplication;
import com.sprintgdp.gdpsprint.exception.ResourceNotFoundException;
import com.sprintgdp.gdpsprint.model.GDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

@RestController
public class GdpController
{
    Date date = new Date();
    long time = date.getTime();
    private static final Logger logger = LoggerFactory.getLogger(GDP.class);
    Timestamp ts = new Timestamp(time);

    // localhost:2020/sprint/names
    @GetMapping(value = "/names", produces = {"application/json"})
    public ResponseEntity<?> getCountries()
    {
        logger.info("/names Accessed at " + ts);
        GdpsprintApplication.myGdpList.gdpList.sort((g1, g2) -> g1.getName().compareToIgnoreCase(g2.getName()));
        return new ResponseEntity<>(GdpsprintApplication.myGdpList.gdpList, HttpStatus.OK);
    }

    // localhost:2020/sprint/economy
    @GetMapping(value = "/economy", produces = {"application/json"})
    public ResponseEntity<?> mostToLeastGpd()
    {
        logger.info("/economy Accessed at " + ts);
        GdpsprintApplication.myGdpList.gdpList.sort((g1,g2) -> g2.getGdp() - g1.getGdp());
        return new ResponseEntity<>(GdpsprintApplication.myGdpList.gdpList, HttpStatus.OK);
    }

    // localhost:2020/sprint/gdp/{country name}
    @GetMapping(value = "/gdp/{name}", produces = {"application/json"})
    public ResponseEntity<?> getCountryByName(@PathVariable String name)
    {
        GDP rtnName;
        logger.info("/gdp/" + name + " Accessed at " + ts);
        if (GdpsprintApplication.myGdpList.findGdp(g -> (g.getName().toUpperCase()).equals(name.toUpperCase())) == null)
        {
            throw new ResourceNotFoundException("Country with name " + name + " not found");
        } else
        {
            rtnName = GdpsprintApplication.myGdpList.findGdp(g -> (g.getName().toUpperCase()).equals(name.toUpperCase()));
        }
        return new ResponseEntity<>(rtnName, HttpStatus.OK);
    }

    // localhost:2020/sprint/country/{id}
    @GetMapping(value = "/country/{id}", produces = {"application/json"})
    public ResponseEntity<?> getCountryById(@PathVariable long id)
    {
        GDP rtnName;
        logger.info("/country/" + id + " Accessed at " + ts);
        if (GdpsprintApplication.myGdpList.findGdp(g -> (g.getId() == id)) == null)
        {
            throw new ResourceNotFoundException("Country with id " + id + " not found");
        } else
        {
            rtnName = GdpsprintApplication.myGdpList.findGdp(g -> (g.getId() == id));
        }
        return new ResponseEntity<>(rtnName, HttpStatus.OK);
    }

    // localhost:2020/sprint/country/stats/median
    @GetMapping(value = "/country/stats/median", produces = {"application/json"})
    public ResponseEntity<?> getCountryMedian()
    {
        logger.info("/country/stats/median/ Accessed at " + ts);
        GdpsprintApplication.myGdpList.gdpList.sort((g1,g2) -> g1.getGdp() - g2.getGdp());
        int middle = GdpsprintApplication.myGdpList.gdpList.size()/2;
        return new ResponseEntity<>(GdpsprintApplication.myGdpList.gdpList.get(middle), HttpStatus.OK);
    }

    // localhost:2020/sprint/economy/greatest/{GDP}
    @GetMapping(value = "/economy/greatest/{GDP}")
    public ModelAndView displayGdp(@PathVariable int GDP)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("gdp");
        ArrayList<GDP> rtnGdp = GdpsprintApplication.myGdpList.findGdps(g -> g.getGdp() >= GDP);
        rtnGdp.sort((g1,g2) -> g2.getGdp() - g1.getGdp());
        mav.addObject("gdpList", rtnGdp);
        return mav;
    }

    // localhost:2020/sprint/economy/table
    @GetMapping(value = "/economy/table")
    public ModelAndView displayGdpTable()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("gdptable");
        GdpsprintApplication.myGdpList.gdpList.sort((g1,g2) -> g2.getGdp() - g1.getGdp());
        mav.addObject("gdpList", GdpsprintApplication.myGdpList.gdpList);
        return mav;
    }
}
