package com.sprintgdp.gdpsprint.model;

import java.util.concurrent.atomic.AtomicLong;

public class GDP
{
    private static AtomicLong counter = new AtomicLong();
    private long id;
    private String name;
    private int gdp;

    public GDP()
    {
    }

    public GDP(String name, int gdp)
    {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.gdp = gdp;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getGdp()
    {
        return gdp;
    }

    public void setGdp(int gdp)
    {
        this.gdp = gdp;
    }

    public long getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "GDP{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gdp=" + gdp +
                '}';
    }
}
