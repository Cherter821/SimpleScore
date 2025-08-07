subprojects {
    group = "${rootProject.group}.worldguard"
}

dependencies {
    api(project(":core"))

    implementation(project(":bukkit:worldguard:v6"))
    implementation(project(":bukkit:worldguard:v7"))

    compileOnly(libs.bukkit)
}