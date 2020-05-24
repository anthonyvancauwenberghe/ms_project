package factories;

import abstracts.AbstractEvent;
import abstracts.AbstractEventFactory;
import enums.AgentShift;
import enums.AgentType;
import events.MachineStartedEvent;
import events.MachineStoppedEvent;
import models.CallAgent;

import java.util.ArrayList;

public class AgentFactory extends AbstractEventFactory {

    /**
     * What type of agent is it CORPORATE|CONSUMER
     */
    protected final AgentType type;

    /**
     * What type of shift is it MORNING|NOON|NIGHT
     */
    protected final AgentShift shift;

    protected int agents;


    public AgentFactory(AgentType type, AgentShift shift, int amount) {
        super("AgentFactory");
        this.type = type;
        this.shift = shift;
        this.agents = amount;
    }

    @Override
    public AbstractEvent[] build() {
        ArrayList<AbstractEvent> events = new ArrayList<>();
        boolean[] roster = this.shift.getRoster();

        ArrayList<Double> startTimes = new ArrayList<>();
        ArrayList<Double> stopTimes = new ArrayList<>();

        boolean active = false;

        for (int h = 0; h < roster.length; h++) {
            if (roster[h] && !active) {
                startTimes.add(h * 3600.0);
                active = true;
            } else if (active && !roster[h]) {

                stopTimes.add(h * 3600.0);
                active = false;
            }
        }

        for (int i = 0; i < this.agents; i++) {
            CallAgent machine = new CallAgent(this.type, this.shift, i);
            for (double startTime : startTimes) {
                events.add(new MachineStartedEvent(startTime, machine));
            }
            for (double stopTime : stopTimes) {
                events.add(new MachineStoppedEvent(stopTime, machine));
            }
        }

        AbstractEvent[] arr = new AbstractEvent[events.size()];
        return events.toArray(arr);
    }
}
