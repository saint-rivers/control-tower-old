# Control Tower

## Setup

Clone the main repo.

```bash
git clone https://github.com/saint-rivers/control-tower.git
```

I've added the config-server configuration in a separate git repo but included it as a git submodule.

```bash
git submodule update --init --recursive
```

You will find the YAML configuration files in the `control-tower-config` folder.

Start all project with this command.

```bash
./deploy.sh
```
