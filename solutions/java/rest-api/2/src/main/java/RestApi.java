import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


class RestApi {
    private final LinkedHashSet<String> userNames = new LinkedHashSet<>();
    private final Map<DebtKey, Double> debts = new HashMap<>();

    record DebtKey(String lender, String borrower) {
    }

    record IouPayload(String lender, String borrower, double amount) {
    }

    RestApi(User... users) {
        for (User user : users) {
            userNames.add(user.name());
            for (Iou iou : user.owedBy()) {
                addDebt(user.name(), iou.name, iou.amount);
            }
        }
    }

    String get(String url) {
        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        response.put("users", jsonArray);
        for (String userName : userNames) {
            User user = reconstructUser(userName);
            JSONObject userObject = createJsonFromUser(user);
            jsonArray.put(userObject);
        }
        return response.toString();
    }

    String get(String url, JSONObject payload) {
        JSONArray payloadUsers = payload.getJSONArray("users");
        Set<String> selectedUsers = new HashSet<>();
        for (int i = 0; i < payloadUsers.length(); i++) {
            selectedUsers.add(payloadUsers.getString(i));
        }

        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        response.put("users", jsonArray);
        for (String userName : userNames) {
            if (selectedUsers.isEmpty() || selectedUsers.contains(userName)) {
                User user = reconstructUser(userName);
                JSONObject userObject = createJsonFromUser(user);
                jsonArray.put(userObject);
            }
        }
        return response.toString();
    }

    String post(String url, JSONObject payload) {
        return switch (url) {
            case "/iou" -> postIou(payload);
            case "/add" -> postAdd(payload);
            default -> throw new IllegalArgumentException(url);
        };
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
        addDebt(iouPayload.lender, iouPayload.borrower, iouPayload.amount);

        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        response.put("users", jsonArray);
        for (String userName : userNames) {
            String lender = iouPayload.lender;
            String borrower = iouPayload.borrower;

            if (userName.equals(lender) || userName.equals(borrower)) {
                User user = reconstructUser(userName);
                jsonArray.put(createJsonFromUser(user));
            }
        }
        return response.toString();
    }

    private User reconstructUser(String userName) {
        Map<String, Double> owes = new TreeMap<>();
        Map<String, Double> owedBy = new TreeMap<>();

        for (var entry : debts.entrySet()) {
            DebtKey key = entry.getKey();
            double amount = entry.getValue();

            if (userName.equals(key.borrower)) {
                owes.put(key.lender(), amount);
            }
            if (userName.equals(key.lender)) {
                owedBy.put(key.borrower(), amount);
            }
        }

        var builder = User.builder();
        builder.setName(userName);
        for (var entry : owes.entrySet()) {
            builder.owes(entry.getKey(), entry.getValue());
        }
        for (var entry : owedBy.entrySet()) {
            builder.owedBy(entry.getKey(), entry.getValue());
        }
        return builder.build();
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

    private IouPayload convertIouPayLoad(JSONObject payload) {
        return new IouPayload(
                payload.getString("lender"),
                payload.getString("borrower"),
                payload.getDouble("amount")
        );
    }

    private void addDebt(
            String lender,
            String borrower,
            double amount) {

        DebtKey opposite = new DebtKey(borrower, lender);

        Double oppositeAmount = debts.get(opposite);

        if (oppositeAmount == null) {
            debts.merge(
                    new DebtKey(lender, borrower),
                    amount,
                    Double::sum);
            return;
        }

        if (oppositeAmount > amount) {
            debts.put(opposite, oppositeAmount - amount);
        } else if (oppositeAmount < amount) {
            debts.remove(opposite);

            debts.put(
                    new DebtKey(lender, borrower), amount - oppositeAmount);
        } else {
            debts.remove(opposite);
        }
    }
}