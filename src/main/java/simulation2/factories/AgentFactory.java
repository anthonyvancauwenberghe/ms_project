package simulation2.factories;

import simulation2.abstracts.AbstractEvent;
import simulation2.abstracts.AbstractEventFactory;
import simulation2.enums.AgentShift;
import simulation2.enums.AgentType;
import simulation2.events.MachineCreatedEvent;
import simulation2.events.MachineStoppedEvent;
import simulation2.models.CallAgent;

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
                events.add(new MachineCreatedEvent(startTime, machine));
            }
            for (double stopTime : stopTimes) {
                events.add(new MachineStoppedEvent(stopTime, machine));
            }
        }

        AbstractEvent[] arr = new AbstractEvent[events.size()];
        return events.toArray(arr);
    }
}
