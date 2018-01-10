package com.jbheng.minimart;

/**
 * Created by jhansen on 1/10/18.
 *
 * Interface for communication between Activity (where Adapter lives)
 * and fragments that need access to it.
 */

public interface AdapterInterface {

    public RecyclerViewAdapter getAdapter();

    public void setAdapter(RecyclerViewAdapter adapter);

}
