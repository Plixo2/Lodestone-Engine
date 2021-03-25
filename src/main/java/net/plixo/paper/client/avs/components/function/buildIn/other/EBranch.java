package net.plixo.paper.client.avs.components.function.buildIn.other;


import net.plixo.paper.client.avs.components.function.other.Execute;

public class EBranch extends Execute {

    public EBranch() {
        super("Branch");
    }

    @Override
    public void execute() {

    }

    void next(int index) {
        if (nextConnection != null) {

            Execute next = nextConnection[index];
            if (next != null) {
                next.hasCalculated = true;

                next.reTrace();
                next.execute();
                next.postExecute();
            }
        }
    }

    @Override
    public void postExecute() {
        next(0);
        next(1);
    }

    @Override
    public void setTypes() {
        this.size = 2;
        super.setTypes();
    }

}
