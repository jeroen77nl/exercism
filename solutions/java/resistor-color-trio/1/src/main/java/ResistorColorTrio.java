class ResistorColorTrio {

    enum PrefixEnum {
      GIGA(1_000_000_000),
      MEGA(1_000_000),
      KILO(1_000);

      private final int value;

      PrefixEnum(int value) {
          this.value = value;
      }

      int getValue() {
          return this.value;
      }
    };

    String label(String[] colors) {

        int numberOfZeroes = ResistorColor.colorCode(colors[2]);
        long numResult = (long)ResistorColorDuo.value(colors) * Math.powExact(10, numberOfZeroes);

        String prefixText = "";
        for (var prefixValue : PrefixEnum.values()) {
            if (numResult >= prefixValue.getValue()) {
                numResult /= prefixValue.getValue();
                prefixText = prefixValue.name().toLowerCase();
                break;
            }
        }
        return numResult + " " + prefixText + "ohms";
    }
}
