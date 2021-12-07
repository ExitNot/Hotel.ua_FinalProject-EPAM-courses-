package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class RequestService {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    public static Optional<Request> getById(Long id) throws JDBCException {
        Optional<Request> req = DAOFactory.getInstance().getRequestDao().getById(id);
        if (req.isPresent()) {
            if (!validateWaitingTime(req.get()))
                req = Optional.empty();
        }
        return req;
    }

    public static List<Request> getByDate(Date from, Date to) throws JDBCException {
        List<Request> out = DAOFactory.getInstance().getRequestDao().getByDate(from, to);
        Iterator<Request> it = out.iterator();
        while (it.hasNext()) {
            if (!validateWaitingTime(it.next()))
                it.remove();
        }
        return out;
    }

    public static List<Request> getByUserId(Long id) throws JDBCException {
        List<Request> out = DAOFactory.getInstance().getRequestDao().getUserRequests(id);
        Iterator<Request> it = out.iterator();
        while (it.hasNext()) {
            if (!validateWaitingTime(it.next()))
                it.remove();
        }
        return out;
    }

    public static List<Request> getBundleRequest(Long id, Date from, Date to) throws JDBCException {
        List<Request> out = DAOFactory.getInstance().getRequestDao().getUserRequests(id);
        log.trace(out);
        Iterator<Request> it = out.iterator();
        while (it.hasNext()) {
            Request r = it.next();
            if (!r.getFrom().equals(from) || !r.getTo().equals(to) || r.getStatus() != Request.Status.ManagerResponse){
                it.remove();
                log.trace("removing " + r);
            }
            if (!validateWaitingTime(r))
                it.remove();
        }
        return out;
    }

    public static List<Request> getByStatus(Request.Status status) throws JDBCException {
        List<Request> out = DAOFactory.getInstance().getRequestDao().getRequestsByStatus(status);
        Iterator<Request> it = out.iterator();
        while (it.hasNext()) {
            if (!validateWaitingTime(it.next()))
                it.remove();
        }
        return out;
    }

    public static List<Request> getAll() throws JDBCException {
        List<Request> out = DAOFactory.getInstance().getRequestDao().getAll();
        Iterator<Request> it = out.iterator();
        while (it.hasNext()) {
            if (!validateWaitingTime(it.next()))
                it.remove();
        }
        return out;
    }

    public static void create(Request request) throws JDBCException {
        DAOFactory.getInstance().getRequestDao().create(request);
    }

    public static void update(Request request) throws JDBCException {
        DAOFactory.getInstance().getRequestDao().update(request);
    }

    public static void delete(long id) throws JDBCException {
        DAOFactory.getInstance().getRequestDao().delete(id);
    }

    private static boolean validateWaitingTime(Request req) throws JDBCException {
        Date today = Util.getToday();
        Calendar c;

        if (req.getManagerAcceptance() != null && (req.getStatus() != Request.Status.Canceled)) {
            c = Calendar.getInstance();
            c.setTime(req.getManagerAcceptance());
            c.add(Calendar.DATE, 2);
            Date deadline = new Date(c.getTimeInMillis());

            if (today.after(deadline)) {
                req.setStatus(Request.Status.Canceled.getValue());
                DAOFactory.getInstance().getRequestDao().update(req);
                log.info("Request status was changed to \"canceled\"");
            }
        }
        if (req.getManagerAcceptance() != null && req.getStatus().equals(Request.Status.Canceled)) {
            c = Calendar.getInstance();
            c.setTime(req.getManagerAcceptance());
            c.add(Calendar.DATE, 4);
            Date deadline = new Date(c.getTimeInMillis());

            if (today.after(deadline)) {
                DAOFactory.getInstance().getRequestDao().delete(req.getId());
                log.info("Request(" + req.getId() + ") was deleted, because was canceled 2 days ago");
                return false;
            }
        }
        return true;
    }
}
