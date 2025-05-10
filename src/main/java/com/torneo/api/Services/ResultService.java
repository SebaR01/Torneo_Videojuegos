package com.torneo.api.Services;

import com.torneo.api.Repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService
{
    @Autowired
    private ResultRepository resultRepository;
}
