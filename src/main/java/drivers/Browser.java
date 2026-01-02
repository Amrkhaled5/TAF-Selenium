package drivers;

public enum Browser {
    CHROME{
        @Override
        public AbstractDriver getDriverFactory() {
            return new ChromeFactory();
        }
    },
    FIREFOX{
        @Override
        public AbstractDriver getDriverFactory() {
            return new FirefoxFactory();
        }
    },
    SAFARI{
        @Override
        public AbstractDriver getDriverFactory() {
            return new SafariFactory();
        }
    },
    EDGE{
        @Override
        public AbstractDriver getDriverFactory() {
            return new EdgeFactory();
        }
    };

    public abstract AbstractDriver getDriverFactory();
}

