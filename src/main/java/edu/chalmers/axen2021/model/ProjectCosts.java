package edu.chalmers.axen2021.model;

import java.util.ArrayList;

/**
 * @author Tilda Grönlund
 * @author Ahmad Al-Aref
 * @author Sara Vardheim
 */

public class ProjectCosts {

    /**
     * Calculates and returns the Connection costs.
     * @param krPerApt cost in kr per apartment.
     * @param totQuantity total quantity of apartments.
     * @return Connection costs of the project.
     */
    public double getConnectionsCost(int krPerApt, int totQuantity) {
        return (krPerApt * totQuantity) / 1000;
    }

    /**
     * Calculates and returns the Client cost
     * @param clientBoa BOA of the client.
     * @param totBoa total BOA of the project.
     * @return Client costs of the project.
     */
    public double getClientCost(int clientBoa, int totBoa) {
        return (clientBoa * totBoa) / 1000;
    }

    /**
     * Calculates and returns the Contract costs.
     * @param contractBoa BOA of the contract.
     * @param totBoa total BOA of the project.
     * @return Contract costs of the project.
     */
    public double getContractCost(int contractBoa, int totBoa) {
        return (contractBoa * totBoa) / 1000;
    }

    /**
     * Calculates and returns the Unforseen costs.
     * @param contractBoa BOA of the contract.
     * @param totBoa total BOA of the project.
     * @param contractPercent percentage of the contract.
     * @return Unforseen costs of the project.
     */
    public double getUnforseenCost(int contractBoa, int totBoa, double contractPercent) {
        return getContractCost(contractBoa, totBoa) * contractPercent;
    }

    /**
     * Calculates and returns the Financial costs.
     * @param totBoa total BOA of the project.
     * @param krPerSqm cost in kr per square meter.
     * @return Financial costs of the project.
     */
    public double getFinancialCost(int totBoa, int krPerSqm) {
        return (totBoa * krPerSqm) / 1000;
    }

    /**
     *
     * @param groundRent the ground rent for the project.
     * @return Site cost for the project.
     */
    public double getSiteCost(int groundRent){
        return groundRent;
    }

    /**
     *
     * @param disusedDeveloperCost disused developer cost (nedlagda byggherre).
     * @return The disused developer cost for the project.
     */
    public double getDisusedDeveloperCost(int disusedDeveloperCost){
        return disusedDeveloperCost;
    }

    /**
     * Checks for every Cost Item in the project if moms should be applied to the Cost Item.
     * If true then the Cost Item's value is multiplied with 0.25 (the tax) and added to the tax sum.
     * @param matrix An ArrayList containing all of the projects ArrayLists of Cost Items.
     * @return The tax sum for the project (mervärdesskatt)
     */
    public double getMervardesskatt(ArrayList<ArrayList<CostItem>> matrix) {
        double moms = 0.0;
        for (ArrayList<CostItem> list : matrix) {
            for (CostItem c : list) {
                if (c.getMoms()) {
                    moms += c.getValue()*0.25;
                }
            }
        }
        return moms / 1000;
    }

    /**
     * Summarizes the total cost of a specified Cost Item in the project.
     * @param list List of Cost Items
     * @return The sum of the Cost Items
     */
    public double getTotalCost(ArrayList<CostItem> list) {
        double total = 0.0;
        for (CostItem c : list) {
            total += c.getValue();
        }
        return total / 1000;
    }

    /**
     * Calculates cost per BOA for a specified cost.
     * @param variable The specified cost.
     * @param totBoa The total BOA for the project.
     * @return The cost per BOA for the specified cost.
     */
    public double getCostPerBoa(int variable, int totBoa){
        return variable/(totBoa*1000);
    }

    /**
     * Calculates cost per BTA for a specified cost.
     * @param variable The specified cost.
     * @param totBta The total BTA for the project.
     * @return
     */
    public double getCostPerBta(int variable, int totBta){
        return variable/(totBta*1000);
    }
}
