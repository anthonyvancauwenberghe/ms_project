package simulation2.processors;

import simulation2.models.Product;
import simulation2.models.Sink;
import simulation2.enums.ProductType;
import configs.BusinessConstraintsConfig;


import javax.naming.ConfigurationException;
import java.util.ArrayList;

public class SinkConstraintAnalyzer {

    protected ProductType productType;
    protected Sink sink;
    protected ArrayList<Product> products;

    protected int totalCallsCount;
    protected int callsOfTypeCount;
    protected double ratioOfCallsOfThisType;
    protected double totalCallTime;
    protected double totalWaitTime;
    protected double averageCallTime;
    protected double averageWaitTime;
    protected ArrayList<Double> waitTimeList = new ArrayList();;
    protected ArrayList<Double> callTimeList = new ArrayList();
    protected ArrayList<Boolean> constraintsSatisfied = new ArrayList();

    protected ArrayList<Double> constraintsRatioLimit = new ArrayList();
    protected ArrayList<Double> constraintTimeLimit = new ArrayList();

    protected ArrayList<Double> ratioViolatingAConstraint = new ArrayList();

    public SinkConstraintAnalyzer(Sink sink, ProductType productType) throws ConfigurationException {
        this.productType = productType;
        this.sink = sink;
        this.products = sink.getProducts();
        this.totalCallsCount = this.products.size();
        this.loadConfig();
        this.parseSink();
        this.calculateSatisfiesAllConstraints();
    }

    private void parseSink() {
        for (int i = 0; i < this.totalCallsCount; i++) {
            ProductType type = this.products.get(i).getType();
            ArrayList<Double> times = this.products.get(i).getTimes();
            if(type == this.productType){
                double waitTime = this.calculateWaitTimeSingleCall(times);
                double callTime = this.calculateCallTimeSingleCall(times);
                this.callsOfTypeCount += 1;
                this.totalWaitTime += waitTime;
                this.totalCallTime += callTime;
                this.waitTimeList.add(waitTime);
                this.callTimeList.add(callTime);
            }
        }
        this.averageCallTime = this.totalCallTime/this.callsOfTypeCount;
        this.averageWaitTime = this.totalWaitTime/this.callsOfTypeCount;
        this.ratioOfCallsOfThisType = ((double) this.callsOfTypeCount) /this.totalCallsCount;
    }

    private double calculateWaitTimeSingleCall(ArrayList<Double> times) {
        return times.get(1) - times.get(0);
    }

    private double calculateCallTimeSingleCall(ArrayList<Double> times) {
        return times.get(2) - times.get(1);
    }

    private boolean calculateSatisfiesAllConstraints() {
        for (int i = 0; i < this.constraintsRatioLimit.size(); i++) {
            constraintsSatisfied.add(this.isSatisfyingAConstraint(this.constraintsRatioLimit.get(i), this.constraintTimeLimit.get(i))
            );
        }
        return this.isSatisfiesAllConstraints();
    }

    private boolean isSatisfyingAConstraint(double limitRatio, double limitTime) {
        int countViolatingConstraint = 0;
        for (int i = 0; i < this.callsOfTypeCount; i++) {
            if(this.waitTimeList.get(i) > limitTime){
                countViolatingConstraint += 1;
            }
        }
        double violatingConstraintRatio = ((double) countViolatingConstraint)/this.callsOfTypeCount;
        this.ratioViolatingAConstraint.add(violatingConstraintRatio);
        if (violatingConstraintRatio > limitRatio) {
            return false;
        }
        return true;
    }

    private void loadConfig() throws ConfigurationException {
        if(this.productType == ProductType.CONSUMER){
            constraintsRatioLimit.add(BusinessConstraintsConfig.CONSUMER_PERCENTAGE_CONSTRAINT_1);
            constraintsRatioLimit.add(BusinessConstraintsConfig.CONSUMER_PERCENTAGE_CONSTRAINT_2);
            constraintTimeLimit.add(BusinessConstraintsConfig.CONSUMER_TIME_CONSTRAINT_1);
            constraintTimeLimit.add(BusinessConstraintsConfig.CONSUMER_TIME_CONSTRAINT_2);
        } else if (this.productType == ProductType.CORPORATE) {
            constraintsRatioLimit.add(BusinessConstraintsConfig.CORPORATE_PERCENTAGE_CONSTRAINT_1);
            constraintsRatioLimit.add(BusinessConstraintsConfig.CORPORATE_PERCENTAGE_CONSTRAINT_2);
            constraintTimeLimit.add(BusinessConstraintsConfig.CORPORATE_TIME_CONSTRAINT_1);
            constraintTimeLimit.add(BusinessConstraintsConfig.CORPORATE_TIME_CONSTRAINT_2);
        } else {
            throw new RuntimeException("Unsupported call type");
        }
        this.checkConfigRatioTimeArraysEqualCountConditions();
    }

    private void checkConfigRatioTimeArraysEqualCountConditions() throws ConfigurationException {
        if(this.constraintsRatioLimit.size() != this.constraintTimeLimit.size()){
            throw new ConfigurationException("Number of rules in percentage within and number served does not match");
        }
    }

    public boolean isSatisfiesAllConstraints(){
        return !this.constraintsSatisfied.contains(false);
    }

    public int getCallsOfTypeCount() {
        return callsOfTypeCount;
    }

    public double getRatioOfCallsOfThisType() {
        return ratioOfCallsOfThisType;
    }

    public double getTotalCallTime() {
        return totalCallTime;
    }

    public double getTotalWaitTime() {
        return totalWaitTime;
    }

    public double getAverageCallTime() {
        return averageCallTime;
    }

    public double getAverageWaitTime() {
        return averageWaitTime;
    }

    public ArrayList<Boolean> getConstraintsSatisfiedList() {
        return constraintsSatisfied;
    }
}
