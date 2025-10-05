plugins {
    id("dev.deftu.gradle.multiversion-root")
}

preprocess {
    "1.21.9-fabric"(1_21_09, "yarn") {
        "1.21.7-fabric"(1_21_07, "yarn") {
            "1.21.5-fabric"(1_21_05, "yarn") {
                "1.20.1-fabric"(1_20_01, "yarn") {
                    "1.16.5-fabric"(1_16_05, "yarn") {
                        "1.16.5-forge"(1_16_05, "srg") {
                            "1.8.9-forge"(1_08_09, "srg")
                        }
                    }
                }
            }
        }
    }
}