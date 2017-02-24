Client: Runs on a cluster node. Gets a job from server and starts working. Can fail/crash.

Server: Feeds the clients jobs. Keeps a view of clients that are alive and working. If a client crashes, gives its job to some other client. Dynamically handles client joining/leaving the system.
