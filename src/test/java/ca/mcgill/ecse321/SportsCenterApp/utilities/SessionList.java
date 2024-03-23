package ca.mcgill.ecse321.SportsCenterApp.utilities;

import ca.mcgill.ecse321.SportsCenterApp.model.Session;

import java.util.List;

public class SessionList {
    private List<Session> sessions;

    public SessionList(List<Session> sessions) {
        this.sessions = sessions;
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
