import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

class RestApi {
    private final User[] users;

    RestApi(User... users) {
        this.users = users;
    }

    String get(String url) {
        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        response.put("users", jsonArray);
        for (User user : users) {
            JSONObject userObject = createJsonFromUser(user);
            jsonArray.put(userObject);
        }
        return response.toString();
    }

    private static JSONObject createJsonFromUser(User user) {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", user.name());

        JSONObject jsonOwes = new JSONObject();
        double balance = 0;
        for (Iou iou : user.owes()) {
            jsonOwes.put(iou.name, iou.amount);
            balance -= iou.amount;
        }
        jsonUser.put("owes", jsonOwes);

        JSONObject jsonOwedBy = new JSONObject();
        for (Iou iou : user.owedBy()) {
            jsonOwedBy.put(iou.name, iou.amount);
            balance += iou.amount;
        }
        jsonUser.put("owedBy", jsonOwedBy);
        jsonUser.put("balance", balance);
        return jsonUser;
    }

    String get(String url, JSONObject payload) {
        JSONArray payloadUsers = payload.getJSONArray("users");
        List<String> selectedUsers = new ArrayList<>();
        for (int i = 0; i < payloadUsers.length(); i++) {
            selectedUsers.add(payloadUsers.getString(i));
        }

        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        response.put("users", jsonArray);
        for (User user : users) {
            if (selectedUsers.isEmpty() || selectedUsers.contains(user.name())) {
                JSONObject userObject = createJsonFromUser(user);
                jsonArray.put(userObject);
            }
        }
        return response.toString();
    }

    String post(String url, JSONObject payload) {
        if (url.equals("/iou")) {
            return postIou(payload);
        } else {
            return postAdd(payload);
        }
    }

    private String postAdd(JSONObject payload) {
        String name = payload.getString("user");
        JSONObject response = new JSONObject();
        response.put("name", name);
        response.put("owes", new JSONObject());
        response.put("owedBy", new JSONObject());
        response.put("balance", 0.0);
        return response.toString();
    }

    private String postIou(JSONObject payload) {
        IouPayload iouPayload = convertIouPayLoad(payload);

        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        response.put("users", jsonArray);
        for (User user : users) {
            if (user.name().equals(iouPayload.lender)) {
                user = createNewUserLending(user, iouPayload);
            } else if (user.name().equals(iouPayload.borrower)) {
                user = createNewUserBorrowing(user, iouPayload);
            } else {
                continue;
            }

            jsonArray.put(createJsonFromUser(user));
        }
        return response.toString();
    }

    private User createNewUserLending(User user, IouPayload iouPayload) {
        Map<String, Double> owes = new TreeMap<>();
        for (Iou iou : user.owes()) {
            owes.put(iou.name, iou.amount);
        }

        Map<String, Double> owedBy = new HashMap<>();
        for (Iou iou : user.owedBy()) {
            owedBy.put(iou.name, iou.amount);
        }

        if (!owedBy.containsKey(iouPayload.borrower)) {
            if (!owes.containsKey(iouPayload.borrower)) {
                owedBy.put(iouPayload.borrower, iouPayload.amount);
            } else {
                double owesAmount = owes.get(iouPayload.borrower);
                if  (owesAmount < iouPayload.amount) {
                    owes.remove(iouPayload.borrower);
                    owedBy.put(iouPayload.borrower, iouPayload.amount - owesAmount);
                } else if (owesAmount == iouPayload.amount) {
                    owes.remove(iouPayload.borrower);
                } else {
                    owes.put(iouPayload.borrower, owesAmount - iouPayload.amount);
                }
            }
        } else {
            owedBy.put(iouPayload.borrower, owedBy.get(iouPayload.borrower) + iouPayload.amount);
        }

        var builder = User.builder().setName(user.name());
        for (var entry : owes.entrySet()) {
            builder.owes(entry.getKey(), entry.getValue());
        }
        for (var entry : owedBy.entrySet()) {
            builder.owedBy(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private User createNewUserBorrowing(User user, IouPayload iouPayload) {
        Map<String, Double> owes = new TreeMap<>();
        for (Iou iou : user.owes()) {
            owes.put(iou.name, iou.amount);
        }

        Map<String, Double> owedBy = new HashMap<>();
        for (Iou iou : user.owedBy()) {
            owedBy.put(iou.name, iou.amount);
        }

        if (!owes.containsKey(iouPayload.lender)) {
            if (!owedBy.containsKey(iouPayload.lender)) {
                owes.put(iouPayload.lender, iouPayload.amount);
            } else {
                double owedByAmount = owedBy.get(iouPayload.lender);
                if  (owedByAmount < iouPayload.amount) {
                    owedBy.remove(iouPayload.lender);
                    owes.put(iouPayload.lender, iouPayload.amount - owedByAmount);
                } else if (owedByAmount == iouPayload.amount) {
                    owedBy.remove(iouPayload.lender);
                } else {
                    owedBy.put(iouPayload.lender, owedByAmount - iouPayload.amount);
                }
            }
        } else {
            owes.put(iouPayload.lender, owes.get(iouPayload.lender) + iouPayload.amount);
        }

        var builder = User.builder().setName(user.name());
        for (var entry : owes.entrySet()) {
            builder.owes(entry.getKey(), entry.getValue());
        }
        for (var entry : owedBy.entrySet()) {
            builder.owedBy(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    record IouPayload(String lender, String borrower, double amount) {
    }

    private IouPayload convertIouPayLoad(JSONObject payload) {
        return new IouPayload(
                payload.getString("lender"),
                payload.getString("borrower"),
                payload.getDouble("amount")
        );
    }
}