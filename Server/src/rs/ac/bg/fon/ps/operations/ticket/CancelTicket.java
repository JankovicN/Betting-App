/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.ticket;

import com.mysql.cj.result.LocalDateTimeValueFactory;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class CancelTicket extends AbstractGenericOperation {

    @Override
    protected void executeOperation(Object param) throws Exception {
        Ticket ticket = (Ticket) param;
        ticket.setState("canceled");
        repository.edit(ticket);
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        if (param == null || !(param instanceof Ticket)) {
            throw new Exception("Invalid data for ticket!");
        }
        Ticket ticket = (Ticket) param;
        Date currentTime = new Date();
        LocalDateTime ticketLocalDate = Instant.ofEpochMilli(ticket.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime dateTime = ticketLocalDate.plus(Duration.of(5, ChronoUnit.MINUTES));
        Date tmfn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        if (currentTime.after(tmfn)) {
            System.out.println("Current time : "+ currentTime + "\nTicket time: "+ tmfn);
            throw new Exception("Time for canceling ticket has passed!");
        }
    }

}
