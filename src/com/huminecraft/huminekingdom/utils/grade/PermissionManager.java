package com.huminecraft.huminekingdom.utils.grade;

import java.util.ArrayList;

public class PermissionManager {

    private ArrayList<GradePermission> permissions;

    public PermissionManager() {
        permissions = new ArrayList<>();

        permissions.add(new GradePermission("kingdom-chat", new ArrayList<String>()));

    }

    public ArrayList<GradePermission> getPermissions() {
        return permissions;
    }

}
