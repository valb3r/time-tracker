package ua.timetracker.administration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ua.timetracker.shared.config.Const.REACTIVE_TX_MANAGER;

@Service("auth")
@RequiredArgsConstructor
public class AuthorizationService {

    @Transactional(REACTIVE_TX_MANAGER)
    public boolean canCreateUsers() {
        return true;
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public boolean canCreateProjects() {
        return true;
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public boolean canAssignRoles() {
        return true;
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public boolean canCreateGroups() {
        return true;
    }

    @Transactional(REACTIVE_TX_MANAGER)
    public boolean canAssignUsersToGroups() {
        return true;
    }
}
