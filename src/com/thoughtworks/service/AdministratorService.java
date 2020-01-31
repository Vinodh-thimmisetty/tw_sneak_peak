package com.thoughtworks.service;

import com.thoughtworks.domain.AdministratorTask;

import java.util.List;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public interface AdministratorService {

    void coordinatePlayers(List<AdministratorTask> tasks);
}
